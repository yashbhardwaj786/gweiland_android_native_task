# gweiland_android_native_task

A sample app for a crypto currency market place
The following technology used in application
1. Android with Kotlin
2. MVVM clean Architecture
3. Data Binding
4. Retrofit for api call
5. Koin for Dependency Injection
6. Lottie for Loader animation effect
7. Navigation Architecture
8. Glide for image loading


# App Flow
1. First click on launcher screen, it will navigate to app in exchange screen.
2. Api call with for getting list of latest crypto-coins with limit = 20, by default sort by market cap in descending order.
3. then we get the all slugs with comma separated to call the info api by which we get the logo icon and show the logo image.
4. Initially we show only 8 results and when user clicks over the view all button then all 20 results will be there. I did this because to use view all button functionality
5. Our first result will show on banner as it was not mentioned in requirement doc.
6. User can search the result based on current list i.e. if there is 8 items in start we will show result from 8 item or if search after viewAll click then we search from 20 items.
7. User can filter the list by following options:
    A. Price - Low to High
    B. Price - High to Low
    C. Volume-24 - Low to High
    D. Volume-24 - High to Low

# Requirements Action
1. Replicate the entire Main screen design as shown in figma. --> Done 
2. Display the top 20 cryptocurrencies with their price in USD as shown in the design. --> Done
3. To fetch the cryptocurrency data use API _getV1CryptocurrencyListingsLatest_. --> Done
4. To get the logo use Metadata V2 of Coinmarketcap Api _MetaDataV2_. --> Done
5. Sort the cryptocurrencies by cmc_rank or Marketcap by default. --> Done
   
# Bonus Point
1. Integrate the ‘Filter’ feature on right top (from the design). Give options to sort the cryptocurrencies by **price, volume_24h** --> Done


![Screenshot_20231205-142459.png](..%2F..%2FDesktop%2Funtitled%20folder%2FScreenshot_20231205-142459.png)
![Screenshot_20231205-142522.png](..%2F..%2FDesktop%2Funtitled%20folder%2FScreenshot_20231205-142522.png)