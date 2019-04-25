package mobilize.mx.testflatmap.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import mobilize.mx.restcore.rest.ProjectRepository;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public class MainVMFactory extends ViewModelProvider.NewInstanceFactory {
    private ProjectRepository repository;

    @Inject
    public MainVMFactory(ProjectRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }

}
