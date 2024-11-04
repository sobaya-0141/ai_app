import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): BreedsResponse
    
    @GET("breed/{breed}/images")
    suspend fun getBreedImages(
        @Path("breed") breed: String
    ): BreedImagesResponse
}

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

data class BreedImagesResponse(
    val message: List<String>,
    val status: String
) 