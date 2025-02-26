package edu.ucne.cristophermarte_p2_ap2.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.cristophermarte_p2_ap2.data.local.database.AdministracionDb
import edu.ucne.cristophermarte_p2_ap2.data.remote.DepositoManagerApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val Base_URL = "https://sagapi-dev.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideEntidadApi(moshi: Moshi): DepositoManagerApi {
        return Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DepositoManagerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAdministracionDb(@ApplicationContext context: Context): AdministracionDb {
        return Room.databaseBuilder(
            context, AdministracionDb::class.java, "Administracion.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEntidadDao(administracionDb: AdministracionDb) = administracionDb.entidadDao()
}