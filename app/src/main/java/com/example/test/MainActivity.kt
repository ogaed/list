

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DynamicCategoryCards(innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun DynamicCategoryCards(modifier: Modifier = Modifier, innerPadding: androidx.compose.foundation.layout.PaddingValues) {
    var attractions by remember { mutableStateOf<List<Attraction>>(emptyList()) }

    LaunchedEffect(key1 = true) {
        attractions = loadAttractionsFromJson() // Load attractions from JSON on initial launch
    }

    LazyColumn(
        modifier = modifier.padding(innerPadding)
    ) {
        items(attractions.groupBy { it.category }) { (category, items) ->
            CategoryCard(
                category = category,
                items = items,
                onClickCategory = { navigateToCategory(it) }
            )
        }
    }
}

@Composable
fun CategoryCard(category: String, items: List<Attraction>, onClickCategory: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClickCategory(category) }, // Navigate to filtered category
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = category,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth()
            )
            // Display details of the first attraction in the category (just an example)
            if (items.isNotEmpty()) {
                Text(
                    text = items[0].bio,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// Data class representing attraction details
data class Attraction(
    val id: Int,
    val email: String,
    val created_at: String,
    val updated_at: String,
    val first_name: String,
    val last_name: String,
    val role: Int,
    val location: String,
    val national_id: Int,
    val phone_number: String,
    val expertise: String,
    val languages: String,
    val experience: String,
    val bio: String,
    val preferences: String?,
    val interests: String?,
    val category: String,
    val average_rating: Double,
    val number_of_ratings: Int
)

// Function to load attractions from JSON using Gson
@Throws(IOException::class)
private fun loadAttractionsFromJson(): List<Attraction> {
    val jsonString = """
      [
        {
          "id": 5,
          "email": "business_attraction4@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Business",
          "last_name": "Attraction4",
          "role": 1,
          "location": "Nairobi",
          "national_id": 1011,
          "phone_number": "797012345",
          "expertise": "Tech Hub",
          "languages": "English",
          "experience": "Innovative spaces for tech conferences and networking events.",
          "bio": "Empower your business with cutting-edge technology at our tech hub.",
          "preferences": null,
          "interests": null,
          "category": "business",
          "average_rating": 4.6,
          "number_of_ratings": 0
        },
        {
          "id": 6,
          "email": "business_attraction5@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Business",
          "last_name": "Attraction5",
          "role": 1,
          "location": "Nairobi",
          "national_id": 1213,
          "phone_number": "797123456",
          "expertise": "Exhibition Center",
          "languages": "English",
          "experience": "Showcase your products and services at our world-class exhibition center.",
          "bio": "Experience the future of trade shows and exhibitions in Dubai.",
          "preferences": null,
          "interests": null,
          "category": "business",
          "average_rating": 4.5,
          "number_of_ratings": 0
        },
        {
          "id": 7,
          "email": "business_attraction6@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Business",
          "last_name": "Attraction6",
          "role": 1,
          "location": "Nairobi",
          "national_id": 1415,
          "phone_number": "797234567",
          "expertise": "Financial District",
          "languages": "English",
          "experience": "Premier destination for international business conferences and finance events.",
          "bio": "Unlock new opportunities in the global financial hub of Shanghai.",
          "preferences": null,
          "interests": null,
          "category": "business",
          "average_rating": 3.8,
          "number_of_ratings": 0
        },
        {
          "id": 8,
          "email": "adventure_attraction4@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Adventure",
          "last_name": "Attraction4",
          "role": 1,
          "location": "Nairobi",
          "national_id": 1617,
          "phone_number": "797345678",
          "expertise": "Zip Line Tours",
          "languages": "English",
          "experience": "Experience breathtaking views and adrenaline-pumping thrills on our zip line tours.",
          "bio": "Embark on an unforgettable adventure above the lush landscapes of Bali.",
          "preferences": null,
          "interests": null,
          "category": "adventure",
          "average_rating": 5,
          "number_of_ratings": 0
        },
        {
          "id": 12,
          "email": "sports_attraction5@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Sports",
          "last_name": "Attraction5",
          "role": 1,
          "location": "Nairobi",
          "national_id": 2425,
          "phone_number": "797890123",
          "expertise": "Ice Rink",
          "languages": "English",
          "experience": "Year-round ice skating and hockey facilities for athletes of all levels.",
          "bio": "Glide across the ice and experience the thrill of winter sports in Moscow.",
          "preferences": null,
          "interests": null,
          "category": "sports",
          "average_rating": 0,
          "number_of_ratings": 0
        },
        {
          "id": 13,
          "email": "sports_attraction6@gmail.com",
          "created_at": "2024-02-01 00:00:00",
          "updated_at": "2024-02-01 00:00:00",
          "first_name": "Sports",
          "last_name": "Attraction6",
          "role": 1,
          "location": "Nairobi",
          "national_id": 2627,
          "phone_number": "797901234",
          "expertise": "Martial Arts Dojo",
          "languages": "English",
          "experience": "Traditional training and instruction in various martial arts disciplines.",
          "bio": "Embark on a journey of self-discovery and physical mastery at our martial arts dojo.",
          "preferences": null,
          "interests": null,
          "category": "sports",
          "average_rating": 0,
          "number_of_ratings": 0
        }
      ]
    """.trimIndent() // Replace with actual JSON data

    val listType = object : TypeToken<List<Attraction>>() {}.type
    return Gson().fromJson(jsonString, listType) ?: emptyList()
}

@Preview(showBackground = true)
@Composable
fun PreviewDynamicCategoryCards() {
    TestTheme {
        DynamicCategoryCards(innerPadding = androidx.compose.foundation.layout.PaddingValues())
    }
}
