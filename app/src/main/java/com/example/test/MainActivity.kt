package com.example.test

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


//val backgroundImage = painterResource(id = R.drawable.background_image)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Use Box to add background image and fixed position
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red) // Replace Color.Red with your desired color
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.background_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                // Content Scaffold
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val attractions by remember { mutableStateOf(loadAttractionsFromJson()) }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        LogoHeader()
                        WelcomeHeader()
                        DynamicCategoryCards(attractions = attractions)
                    }
                }
            }
        }
    }
}

    @Composable
    fun LogoHeader() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            // Add your logo image here
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp) // Adjust size as needed
            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(
//                text = "Welcome to Nairobi City Tourism and Culture",
//                style = MaterialTheme.typography.headlineLarge,
//            )
        }
    }



@Composable
fun WelcomeHeader() {
    Text(
        text = "Welcome to Nairobi City Tourism and Culture Manouvers",
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun DynamicCategoryCards(attractions: List<Attraction>, modifier: Modifier = Modifier) {
    val categories = attractions.groupBy { it.category }.keys.toList()

    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        categories.forEach { category ->
            AnimatedCategoryCard(category = category, attractions = attractions)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

val categoryImages = mapOf(
   "Business Tourism and culture" to R.drawable.business_image,
    "Adventure Tourism and culture" to R.drawable.adventure,
    "Sports Tourism and Culture" to R.drawable.sports,
    "Heritage Tourism and Culture" to R.drawable.heritage,
    "Conferencing Tourism and Culture" to R.drawable.conference,
    "Medical Tourism and Culture" to R.drawable.medical,
    "Eco-Tourism and Culture" to R.drawable.eco,
    "Matatu Tourism and Culture" to R.drawable.matatu,
    "Agricultural Tourism and Culture" to R.drawable.agric,
    // Add more categories as needed
)

@Composable
fun AnimatedCategoryCard(category: String, attractions: List<Attraction>) {
    val context = LocalContext.current
    val attractionsInCategory = attractions.filter { it.category == category }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize() // Animates the content size changes
            .clickable {
                expanded = !expanded // Toggle expanded state
            },
        elevation = CardDefaults.cardElevation(48.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display category image
            categoryImages[category]?.let { image ->
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust height as needed
                )
            }
            Text(
                text = category,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "View More",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF3366CC)), // Example color code
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        val intent = Intent(context, CategoryDetailsActivity::class.java).apply {
                            putExtra("category", category)
                        }
                        context.startActivity(intent)
                    }
                    .padding(bottom = 8.dp)
            )
        }
    }
}


private fun loadAttractionsFromJson(): List<Attraction> {
    val jsonString = """
        [
          
          {
            "id": 5,
            "email": "business_attraction4@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Business",
            "last_name": "Attraction4",
            "role": 1,
            "location": "Nairobi",
            "national_id": 1011,
            "phone_number": 797012345,
            "expertise": "Tech Hub",
            "languages": "English",
            "experience": "Innovative spaces for tech conferences and networking events.",
            "bio": "Empower your business with cutting-edge technology at our tech hub.",
            "preferences": null,
            "interests": null,
            "category": "Business Tourism and culture",
            "average_rating": 4.6,
            "number_of_ratings": 0
          },
          {
            "id": 6,
            "email": "business_attraction5@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Business",
            "last_name": "Attraction5",
            "role": 1,
            "location": "Nairobi",
            "national_id": 1213,
            "phone_number": 797123456,
            "expertise": "Exhibition Center",
            "languages": "English",
            "experience": "Showcase your products and services at our world-class exhibition center.",
            "bio": "Experience the future of trade shows and exhibitions in Dubai.",
            "preferences": null,
            "interests": null,
            "category": "Business Tourism and culture",
            "average_rating": 4.5,
            "number_of_ratings": 0
          },
          {
            "id": 7,
            "email": "business_attraction6@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Business",
            "last_name": "Attraction6",
            "role": 1,
            "location": "Nairobi",
            "national_id": 1415,
            "phone_number": 797234567,
            "expertise": "Financial District",
            "languages": "English",
            "experience": "Premier destination for international business conferences and finance events.",
            "bio": "Unlock new opportunities in the global financial hub of Shanghai.",
            "preferences": null,
            "interests": null,
            "category": "Business Tourism and culture",
            "average_rating": 3.8,
            "number_of_ratings": 0
          },
          {
            "id": 8,
            "email": "adventure_attraction4@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Adventure",
            "last_name": "Attraction4",
            "role": 1,
            "location": "Nairobi",
            "national_id": 1617,
            "phone_number": 797345678,
            "expertise": "Zip Line Tours",
            "languages": "English",
            "experience": "Experience breathtaking views and adrenaline-pumping thrills on our zip line tours.",
            "bio": "Embark on an unforgettable adventure above the lush landscapes of Bali.",
            "preferences": null,
            "interests": null,
            "category": "Adventure Tourism and culture",
            "average_rating": 5,
            "number_of_ratings": 0
          },
          {
            "id": 12,
            "email": "sports_attraction5@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Sports",
            "last_name": "Attraction5",
            "role": 1,
            "location": "Nairobi",
            "national_id": 2425,
            "phone_number": 797890123,
            "expertise": "Ice Rink",
            "languages": "English",
            "experience": "Year-round ice skating and hockey facilities for athletes of all levels.",
            "bio": "Glide across the ice and experience the thrill of winter sports in Moscow.",
            "preferences": null,
            "interests": null,
            "category": "Sports Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 13,
            "email": "sports_attraction6@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Sports",
            "last_name": "Attraction6",
            "role": 1,
            "location": "Nairobi",
            "national_id": 2627,
            "phone_number": 797901234,
            "expertise": "Martial Arts Dojo",
            "languages": "English",
            "experience": "Traditional training and instruction in various martial arts disciplines.",
            "bio": "Embark on a journey of self-discovery and physical mastery at our martial arts dojo.",
            "preferences": null,
            "interests": null,
            "category": "Sports Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 14,
            "email": "heritage_attraction4@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Heritage",
            "last_name": "Attraction4",
            "role": 1,
            "location": "Nairobi",
            "national_id": 2829,
            "phone_number": 797012345,
            "expertise": "Pyramid Complex",
            "languages": "English",
            "experience": "Ancient wonder of the world and UNESCO World Heritage Site.",
            "bio": "Explore the mysteries of ancient Egypt at the pyramid complex in Cairo.",
            "preferences": null,
            "interests": null,
            "category": "Heritage Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 16,
            "email": "heritage_attraction6@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Heritage",
            "last_name": "Attraction6",
            "role": 1,
            "location": "Nairobi",
            "national_id": 3233,
            "phone_number": 797234567,
            "expertise": "Acropolis",
            "languages": "English",
            "experience": "Iconic symbol of ancient Greece and architectural masterpiece.",
            "bio": "Step back in time and experience the glory of ancient Athens at the Acropolis.",
            "preferences": null,
            "interests": null,
            "category": "Heritage Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 18,
            "email": "conferencing_attraction2@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Conferencing",
            "last_name": "Attraction2",
            "role": 1,
            "location": "Nairobi",
            "national_id": 3637,
            "phone_number": 797456789,
            "expertise": "Exhibition Hall",
            "languages": "English",
            "experience": "Versatile venue for exhibitions, conferences, and cultural events.",
            "bio": "Experience the elegance of Parisian hospitality at our exhibition hall.",
            "preferences": null,
            "interests": null,
            "category": "Conferencing Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 19,
            "email": "conferencing_attraction3@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Conferencing",
            "last_name": "Attraction3",
            "role": 1,
            "location": "Nairobi",
            "national_id": 3839,
            "phone_number": 797567890,
            "expertise": "Meeting Rooms",
            "languages": "English",
            "experience": "State-of-the-art facilities for corporate meetings and seminars.",
            "bio": "Elevate your business meetings in the dynamic city of Singapore.",
            "preferences": null,
            "interests": null,
            "category": "Conferencing Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 21,
            "email": "medical_attraction2@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Medical",
            "last_name": "Attraction2",
            "role": 1,
            "location": "Nairobi",
            "national_id": 4243,
            "phone_number": 797789012,
            "expertise": "Medical Spa",
            "languages": "English",
            "experience": "Advanced medical treatments and therapeutic spa services.",
            "bio": "Rejuvenate your body and mind at our luxurious medical spa in Seoul.",
            "preferences": null,
            "interests": null,
            "category": "Medical Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 22,
            "email": "medical_attraction3@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Medical",
            "last_name": "Attraction3",
            "role": 1,
            "location": "Nairobi",
            "national_id": 4445,
            "phone_number": 797890123,
            "expertise": "Specialty Hospital",
            "languages": "English",
            "experience": "World-class medical care and specialized treatments.",
            "bio": "Discover excellence in healthcare at our specialty hospital in Istanbul.",
            "preferences": null,
            "interests": null,
            "category": "Medical Tourism and Culture",
            "average_rating": 0,
            "number_of_ratings": 0
          },
          {
            "id": 23,
            "email": "eco_attraction1@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Eco",
            "last_name": "Attraction1",
            "role": 1,
            "location": "Nairobi",
            "national_id": 4647,
            "phone_number": 797901234,
            "expertise": "Rainforest Lodge",
            "languages": "English",
            "experience": "Sustainable accommodations amidst lush rainforest surroundings.",
            "bio": "Immerse yourself in nature and support conservation efforts at our rainforest lodge.",
            "preferences": null,
            "interests": null,
            "category": "Eco-Tourism and Culture",
            "average_rating": 4.5,
            "number_of_ratings": 5
          },
          {
            "id": 29,
            "email": "matatu_attraction1@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Matatu",
            "last_name": "Attraction1",
            "role": 1,
            "location": "Nairobi",
            "national_id": 5859,
            "phone_number": 797567890,
            "expertise": "Matatu Tour",
            "languages": "English",
            "experience": "Guided tours of Nairobi's vibrant matatu culture and street art.",
            "bio": "Experience the energy and creativity of Nairobi's matatu culture on our immersive tours.",
            "preferences": null,
            "interests": null,
            "category": "Matatu Tourism and Culture",
            "average_rating": 5,
            "number_of_ratings": 0
          },
          {
            "id": 9,
            "email": "adventure_attraction5@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Adventure",
            "last_name": "Attraction5",
            "role": 1,
            "location": "Nairobi",
            "national_id": 1819,
            "phone_number": 797456789,
            "expertise": "Bungee Jumping Center",
            "languages": "English",
            "experience": "Home to the world's first commercial bungee jumping site.",
            "bio": "Take the plunge and experience the ultimate adrenaline rush in Queenstown.",
            "preferences": null,
            "interests": null,
            "category": "Adventure Tourism and culture",
            "average_rating": 3,
            "number_of_ratings": 0
          },
          {
            "id": 10,
            "email": "adventure_attraction6@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Adventure",
            "last_name": "Attraction6",
            "role": 1,
            "location": "Nairobi",
            "national_id": 2021,
            "phone_number": 797567890,
            "expertise": "Ski Resort",
            "languages": "English",
            "experience": "Premier destination for skiing and snowboarding enthusiasts.",
            "bio": "Hit the slopes and experience world-class skiing in Whistler.",
            "preferences": null,
            "interests": null,
            "category": "Adventure Tourism and culture",
            "average_rating": 5,
            "number_of_ratings": 0
          },
          {
            "id": 11,
            "email": "sports_attraction4@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Sports",
            "last_name": "Attraction4",
            "role": 1,
            "location": "Nairobi",
            "national_id": 2223,
            "phone_number": 797789012,
            "expertise": "Athletic Stadium",
            "languages": "English",
            "experience": "State-of-the-art facilities for track and field events and athletic competitions.",
            "bio": "Train like an Olympian at our athletic stadium in Berlin.",
            "preferences": null,
            "interests": null,
            "category": "Sports Tourism and Culture",
            "average_rating": 4.7,
            "number_of_ratings": 0
          },
          {
            "id": 15,
            "email": "heritage_attraction5@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Heritage",
            "last_name": "Attraction5",
            "role": 1,
            "location": "Nairobi",
            "national_id": 3031,
            "phone_number": 797123456,
            "expertise": "Inca Ruins",
            "languages": "English",
            "experience": "Lost city of the Incas and one of the New Seven Wonders of the World.",
            "bio": "Journey through history and marvel at the architectural marvels of Machu Picchu.",
            "preferences": null,
            "interests": null,
            "category": "Heritage Tourism and Culture",
            "average_rating": 4.7,
            "number_of_ratings": 0
          },
          {
            "id": 17,
            "email": "conferencing_attraction1@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Conferencing",
            "last_name": "Attraction1",
            "role": 1,
            "location": "Nairobi",
            "national_id": 3435,
            "phone_number": 797345678,
            "expertise": "Convention Center",
            "languages": "English",
            "experience": "Flexible spaces for conventions, trade shows, and special events.",
            "bio": "Host your next event in the entertainment capital of the world.",
            "preferences": null,
            "interests": null,
            "category": "Conferencing Tourism and Culture",
            "average_rating": 5,
            "number_of_ratings": 0
          },
          {
            "id": 20,
            "email": "medical_attraction1@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Medical",
            "last_name": "Attraction1",
            "role": 1,
            "location": "Nairobi",
            "national_id": 4041,
            "phone_number": 797678901,
            "expertise": "Wellness Resort",
            "languages": "English",
            "experience": "Holistic retreat for rejuvenation and wellness treatments.",
            "bio": "Embark on a journey to health and vitality at our wellness resort in Bangkok.",
            "preferences": null,
            "interests": null,
            "category": "Medical Tourism and Culture",
            "average_rating": 3.8,
            "number_of_ratings": 0
          },
          {
            "id": 24,
            "email": "eco_attraction2@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Eco",
            "last_name": "Attraction2",
            "role": 1,
            "location": "Nairobi",
            "national_id": 4849,
            "phone_number": 797012345,
            "expertise": "Fjord Cruise",
            "languages": "English",
            "experience": "Scenic boat tours through breathtaking fjords and coastal landscapes.",
            "bio": "Experience the beauty of Norway's natural wonders on our eco-friendly fjord cruises.",
            "preferences": null,
            "interests": null,
            "category": "Eco-Tourism and Culture",
            "average_rating": 4,
            "number_of_ratings": 0
          },
          {
            "id": 25,
            "email": "eco_attraction3@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Eco",
            "last_name": "Attraction3",
            "role": 1,
            "location": "Nairobi",
            "national_id": 5051,
            "phone_number": 797123456,
            "expertise": "Wildlife Sanctuary",
            "languages": "English",
            "experience": "Preserved habitats for observing diverse species of flora and fauna.",
            "bio": "Explore the Galápagos Islands and encounter unique wildlife in their natural environment.",
            "preferences": null,
            "interests": null,
            "category": "Eco-Tourism and Culture",
            "average_rating": 4.4,
            "number_of_ratings": 0
          },
          {
            "id": 26,
            "email": "agricultural_attraction1@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Agricultural",
            "last_name": "Attraction1",
            "role": 1,
            "location": "Nairobi",
            "national_id": 5253,
            "phone_number": 797234567,
            "expertise": "Vineyard Tour",
            "languages": "English",
            "experience": "Guided tours of picturesque vineyards and wine tasting experiences.",
            "bio": "Savor the flavors of Tuscany on our vineyard tours and wine tastings.",
            "preferences": null,
            "interests": null,
            "category": "Agricultural Tourism and Culture",
            "average_rating": 4.6,
            "number_of_ratings": 0
          },
          {
            "id": 27,
            "email": "agricultural_attraction2@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Agricultural",
            "last_name": "Attraction2",
            "role": 1,
            "location": "Nairobi",
            "national_id": 5455,
            "phone_number": 797345678,
            "expertise": "Farm Stay",
            "languages": "English",
            "experience": "Rural accommodations on working farms with hands-on agricultural activities.",
            "bio": "Experience farm life and authentic Japanese hospitality on our farm stays in Hokkaido.",
            "preferences": null,
            "interests": null,
            "category": "Agricultural Tourism and Culture",
            "average_rating": 3.8,
            "number_of_ratings": 0
          },
          {
            "id": 28,
            "email": "agricultural_attraction3@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Agricultural",
            "last_name": "Attraction3",
            "role": 1,
            "location": "Nairobi",
            "national_id": 5657,
            "phone_number": 797456789,
            "expertise": "Lavender Fields",
            "languages": "English",
            "experience": "Scenic landscapes of blooming lavender fields and lavender product workshops.",
            "bio": "Immerse yourself in the beauty and fragrance of Provence's lavender fields.",
            "preferences": null,
            "interests": null,
            "category": "Agricultural Tourism and Culture",
            "average_rating": 4.5,
            "number_of_ratings": 0
          },
          {
            "id": 30,
            "email": "matatu_attraction2@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Matatu",
            "last_name": "Attraction2",
            "role": 1,
            "location": "Nairobi",
            "national_id": 6061,
            "phone_number": 797678901,
            "expertise": "Matatu Festival",
            "languages": "English",
            "experience": "Annual celebration of matatu culture with music, dance, and street performances.",
            "bio": "Join the festivities and celebrate the vibrant spirit of Kampala's matatu culture.",
            "preferences": null,
            "interests": null,
            "category": "Matatu Tourism and Culture",
            "average_rating": 5,
            "number_of_ratings": 0
          },
          {
            "id": 31,
            "email": "matatu_attraction3@gmail.com",
            "created_at": "2024-02-01 00:00:00",
            "updated_at": "2024-02-01 00:00:00",
            "jti": null,
            "first_name": "Matatu",
            "last_name": "Attraction3",
            "role": 1,
            "location": "Nairobi",
            "national_id": 6263,
            "phone_number": 797789012,
            "expertise": "Matatu Art Workshop",
            "languages": "English",
            "experience": "Hands-on workshops on matatu customization and urban art.",
            "bio": "Unleash your creativity and leave your mark on Lagos's matatu scene.",
            "preferences": null,
            "interests": null,
            "category": "Matatu Tourism and Culture",
            "average_rating": 5,
            "number_of_ratings": 0
          }
        ]

    """.trimIndent()

    val listType = object : TypeToken<List<Attraction>>() {}.type
    return Gson().fromJson(jsonString, listType) ?: emptyList()
}
