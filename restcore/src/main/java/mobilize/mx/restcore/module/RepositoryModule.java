package mobilize.mx.restcore.module;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mobilize.mx.restcore.rest.ProjectRepository;
/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
@Module
public class RepositoryModule {

    /****************FOR PASSING CONTEXT TO SINGLETON*/
    private final Context context;

    public RepositoryModule(@Named("context") Context context) {
        this.context = context;
    }

    /****************THIS PROVIDE THE "INSTANCE" OF THE SINGLETON */
    @Singleton
    @Provides
    ProjectRepository projectRepository (){
        return ProjectRepository.getInstance(context);
    }

    /****************FOR PASSING CONTEXT TO MODULE*/
    @Provides //scope is not necessary for parameters stored within the module
    @Named("context")
    public Context context() {
        return context;
    }
}
