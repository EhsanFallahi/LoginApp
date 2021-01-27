package com.ehsanfallahi.loginapp.di.module

import android.content.Context
import com.ehsanfallahi.loginapp.data.UserPreferences
import com.ehsanfallahi.loginapp.data.database.UsersDatabase
import com.ehsanfallahi.loginapp.data.database.UsersLoginDao
import com.ehsanfallahi.loginapp.data.remoteData.RemoteData
import com.ehsanfallahi.loginapp.data.repository.Repository
import com.ehsanfallahi.loginapp.data.service.ApiService
import com.ehsanfallahi.loginapp.util.ConnectivityInterceptor
import com.ehsanfallahi.loginapp.util.Constant.Companion.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {


    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ConnectivityInterceptor(context))
            .build() }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,okHttpClient: OkHttpClient)=Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
    .build()

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideUserRemoteData(apiService: ApiService) = RemoteData(apiService)

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext appContext: Context) = UsersDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideNewsDao(db: UsersDatabase) = db.usersLoginDao

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext appContext: Context) = UserPreferences(appContext)

    @Singleton
    @Provides
    fun provideRepository(remoteData: RemoteData, usersLoginDao: UsersLoginDao,preferences: UserPreferences)
    =Repository(remoteData,usersLoginDao,preferences)

}