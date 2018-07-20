package com.example.joelwasserman.androidbletutorial.Interface;

import com.example.joelwasserman.androidbletutorial.Pojo.CommonParentPojo;
import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoAddLocation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface setChildStatusActiveInterface {

    String user_id="";
   /* @GET("Parents/list/{parent_id}")
    Call<ParentPojoLogin> doGetListResources(@Path("parent_id") String parent_id);
*/

   @FormUrlEncoded
   @POST("child/activateChild/")
   Call<CommonParentPojo> doGetListResources(@Field("child_id") String child_id, @Field("status") String status, @Field("mac_id") String mac_id);

}
