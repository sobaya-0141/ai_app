import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class DogListViewModel(
    private val repository: DogRepository = DogRepository()
) : ViewModel() {
    val dogImages = repository.getBreedImagesPager("hound").cachedIn(viewModelScope)
} 