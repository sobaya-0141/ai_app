import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun DogListScreen(
    viewModel: DogListViewModel = viewModel(),
    onDogSelected: (String) -> Unit
) {
    val pagingItems = viewModel.dogImages.collectAsLazyPagingItems()
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(pagingItems.itemCount) { index ->
            pagingItems[index]?.let { imageUrl ->
                DogImageItem(
                    imageUrl = imageUrl,
                    onClick = { onDogSelected(imageUrl) }
                )
            }
        }
    }
}

@Composable
fun DogImageItem(
    imageUrl: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
} 