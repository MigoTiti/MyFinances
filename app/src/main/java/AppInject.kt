import android.app.Activity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lucasrodrigues.myfinances.framework.AlertService
import com.lucasrodrigues.myfinances.framework.DialogService
import com.lucasrodrigues.myfinances.framework.NavigationService
import com.lucasrodrigues.myfinances.framework.ResourceService
import com.lucasrodrigues.myfinances.interactor.*
import com.lucasrodrigues.myfinances.model.FinancialEntry
import com.lucasrodrigues.myfinances.repository.AuthRepository
import com.lucasrodrigues.myfinances.repository.FinancialEntryRepository
import com.lucasrodrigues.myfinances.utils.Constants
import com.lucasrodrigues.myfinances.view.BaseActivity
import com.lucasrodrigues.myfinances.view_model.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

object AppInject {
    private val useCaseModule = module {
        factory { HasLoggedUser(get()) }
        factory { Login(get()) }
        factory { Logout(get(), get()) }
        factory { Register(get()) }
        factory { GetLoggedUser(get()) }
        factory { ObserveUserBalance(get()) }
        factory { CreateEntry(get()) }
        factory { UpdateEntry(get()) }
        factory { DeleteEntry(get()) }
        factory { ObserveExpenses(get()) }
        factory { ObserveRevenues(get()) }
    }

    private val viewModelModule = module {
        viewModel { (activity: BaseActivity<*>) ->
            SplashViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                hasLoggedUser = get()
            )
        }

        viewModel { (activity: BaseActivity<*>) ->
            LoginViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                login = get()
            )
        }

        viewModel { (activity: BaseActivity<*>) ->
            RegisterViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                register = get()
            )
        }

        viewModel { (activity: BaseActivity<*>) ->
            MainViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                observeUserBalance = get(),
                getLoggedUser = get(),
                logout = get()
            )
        }

        viewModel { (activity: BaseActivity<*>, initialEntry: FinancialEntry) ->
            ModifyEntryViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                dialogService = get { parametersOf(activity) },
                initialEntry = initialEntry,
                createEntry = get(),
                deleteEntry = get(),
                updateEntry = get()
            )
        }

        viewModel { (activity: BaseActivity<*>, entryType: Constants.EntryType) ->
            ListEntriesViewModel(
                navigationService = activity.navigationService,
                alertService = activity.alertService,
                entryType = entryType,
                observeExpenses = get(),
                observeRevenues = get()
            )
        }
    }

    private val repositoryModule = module {
        single {
            AuthRepository(
                firebaseAuth = get()
            )
        }

        single {
            FinancialEntryRepository(
                firestore = get(),
                authRepository = get()
            )
        }
    }

    private val firebaseModule = module {
        factory { Firebase.auth }
        factory { Firebase.firestore }
    }

    private val frameworkModule = module {
        single { ResourceService(androidContext().applicationContext) }

        factory { (activity: Activity) -> NavigationService(activity) }

        factory { (activity: Activity) ->
            AlertService(
                resourceService = get(),
                activity = activity
            )
        }

        factory { (activity: BaseActivity<*>) -> DialogService(activity = activity) }
    }

    val modules: List<Module>
        get() = listOf(
            firebaseModule,
            frameworkModule,
            repositoryModule,
            viewModelModule,
            useCaseModule
        )
}