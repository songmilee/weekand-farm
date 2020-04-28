package mi.song.weekand.farm.repository.network;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DBService {

    @GET("corp_list/")
    Call<JSONObject> getCorpList();
}
