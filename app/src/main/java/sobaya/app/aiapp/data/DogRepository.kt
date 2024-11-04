import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(DogApi::class.java)

    fun getBreedImagesPager(breed: String): Flow<PagingData<String>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DogPagingSource(api, breed)
            }
        ).flow
    }
}

private class DogPagingSource(
    private val api: DogApi,
    private val breed: String
) : PagingSource<Int, String>() {
    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        return try {
            val page = params.key ?: 0
            val response = api.getBreedImages(breed)
            val images = response.message
            val startIndex = page * params.loadSize
            val endIndex = minOf((page + 1) * params.loadSize, images.size)

            LoadResult.Page(
                data = images.subList(startIndex, endIndex),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (endIndex >= images.size) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
} 