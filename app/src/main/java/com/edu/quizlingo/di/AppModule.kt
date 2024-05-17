package com.edu.quizlingo.di

import android.content.Context
import androidx.room.Room
import com.edu.quizlingo.repository.QuizlingoRepository
import com.edu.quizlingo.room.QuizLingoAppDao
import com.edu.quizlingo.room.QuizLingoAppDataBase
import com.edu.quizlingo.util.Constant
import com.edu.quizlingo.util.EventBusShared
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//this is the app module
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun quizLingoAppRepository(dao: QuizLingoAppDao) = QuizlingoRepository(dao)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): QuizLingoAppDataBase {
        return Room.databaseBuilder(
            context,
            QuizLingoAppDataBase::class.java,
            Constant.QUIZ_LINGO_APP_DB_NAME)
            .fallbackToDestructiveMigration()
            //replace the old database
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideNoteAppDao(quizLingoAppDataBase: QuizLingoAppDataBase): QuizLingoAppDao {
        return quizLingoAppDataBase.quizLingoAppDAO()
    }

    //provide event bus
    @Provides
    @Singleton
    fun provideEventBus() = EventBusShared()
}