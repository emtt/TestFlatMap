package mobilize.mx.restcore.rest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import mobilize.mx.restcore.Constants;
import mobilize.mx.restcore.model.Posts;
import mobilize.mx.restcore.model.User;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public interface ApiInterface {
    @GET(Constants.GET_POSTS)
    Single<List<Posts>> getPosts(@Query("userId") int userId);

    @GET(Constants.GET_USERS)
    Observable<List<User>> getUsers();
}
