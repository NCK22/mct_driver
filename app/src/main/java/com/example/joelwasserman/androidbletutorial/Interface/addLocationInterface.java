package com.example.joelwasserman.androidbletutorial.Interface;

import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoAddLocation;
import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoStudProf;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface addLocationInterface {

    String user_id="";
   /* @GET("Parents/list/{parent_id}")
    Call<ParentPojoLogin> doGetListResources(@Path("parent_id") String parent_id);
*/

   @FormUrlEncoded
   @POST("Location/addLocation/")
   Call<ParentPojoAddLocation> doGetListResources(@Field("latitude") String latitude, @Field("longitude") String longitude,
                                                  @Field("driver_id") String driver_id,@Field("mac_id") String mac_id);

}
