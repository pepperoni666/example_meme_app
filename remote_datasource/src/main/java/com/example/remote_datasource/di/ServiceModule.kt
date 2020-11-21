package com.example.remote_datasource.di

import com.example.remote_datasource.RemoteRepository
import com.example.remote_datasource.UseCase
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceModule{

    private const val BASE_URL = "http://10.0.2.2:3000/"

    val serviceModule = module {

        single {
            // Define the interceptor, add authentication headers
            val interceptor = Interceptor { chain ->
                val newRequest: Request =
                    chain.request().newBuilder()//.addHeader("User-Agent", "Retrofit-Sample-App")
                        .build()
                chain.proceed(newRequest)
            }

            val builder = OkHttpClient.Builder()

            // Add the interceptor to OkHttpClient
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.networkInterceptors().add(httpLoggingInterceptor)
            builder.networkInterceptors().add(interceptor)
            val client = builder.build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
        }

        factory { RemoteRepository(get()) }

        factory { UseCase(get()) }
    }
}