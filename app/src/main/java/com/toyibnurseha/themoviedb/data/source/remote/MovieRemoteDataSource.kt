package com.toyibnurseha.themoviedb.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.toyibnurseha.themoviedb.api.RetrofitInstance
import com.toyibnurseha.themoviedb.data.response.movie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.movie.MovieResponse
import com.toyibnurseha.themoviedb.data.response.show.DetailShowEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowResponse
import com.toyibnurseha.themoviedb.utils.EspressoIdlingResource.decrement
import com.toyibnurseha.themoviedb.utils.EspressoIdlingResource.increment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRemoteDataSource {

    companion object {
        const val TAG = "RemoteDataSource"
        @Volatile
        private var instance: MovieRemoteDataSource? = null

        fun getInstance(): MovieRemoteDataSource =
            instance ?: synchronized(this) {
                MovieRemoteDataSource().apply { instance = this }
            }
    }

    fun getPopularMovies(): LiveData<APIResponse<List<MovieEntity>>> {
        val result = MutableLiveData<APIResponse<List<MovieEntity>>>()
        increment()

        RetrofitInstance.api.getPopularMovies(1).enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                result.value = APIResponse.success(response.body()?.results as List<MovieEntity>)
                decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                decrement()
            }
        })

        return result
    }

    fun getPopularShows(): LiveData<APIResponse<List<TVShowEntity>>> {
        val result = MutableLiveData<APIResponse<List<TVShowEntity>>>()
        increment()

        RetrofitInstance.api.getPopularShow(1).enqueue(object: Callback<TVShowResponse> {
            override fun onResponse(
                call: Call<TVShowResponse>,
                response: Response<TVShowResponse>
            ) {
                result.value = APIResponse.success(response.body()?.results as List<TVShowEntity>)
                decrement()
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                decrement()
            }
        })

        return result
    }

    fun getMovieDetail(movieId: Int) : LiveData<APIResponse<DetailMovieEntity>> {
        val result = MutableLiveData<APIResponse<DetailMovieEntity>>()

        increment()

        RetrofitInstance.api.getDetailMovie(movieId).enqueue(object : Callback<DetailMovieEntity> {
            override fun onResponse(call: Call<DetailMovieEntity>, response: Response<DetailMovieEntity>) {
                result.value = APIResponse.success(response.body() as DetailMovieEntity)

                decrement()
            }

            override fun onFailure(call: Call<DetailMovieEntity>, throwable: Throwable) {
                Log.e(TAG, "Failure ${throwable.message}")

                decrement()
            }
        })

        return result
    }

    fun getShowDetail(showId: Int) : LiveData<APIResponse<DetailShowEntity>> {
        val result = MutableLiveData<APIResponse<DetailShowEntity>>()

        increment()

        RetrofitInstance.api.getDetailShow(showId).enqueue(object : Callback<DetailShowEntity> {
            override fun onResponse(
                call: Call<DetailShowEntity>,
                response: Response<DetailShowEntity>
            ) {
                result.value = APIResponse.success(response.body() as DetailShowEntity)

                decrement()
            }

            override fun onFailure(call: Call<DetailShowEntity>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                decrement()
            }
        })

        return result
    }


    interface LoadMovieDetailCallback {
        fun responseMovieDetail(response: DetailMovieEntity)
    }

    interface LoadShowCallback {
        fun responsePopularShow(response: List<TVShowEntity>)
    }

    interface LoadShowDetailCallback {
        fun responseDetailShow(response: DetailShowEntity)
    }
}