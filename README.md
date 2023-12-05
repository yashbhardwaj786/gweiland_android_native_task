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

# Main Screen
![Screenshot_20231205-142459](https://github.com/KaulSalil/TT_CryptoMarket/assets/8721680/f6bf3256-ea0f-487c-a7b9-546d0c72372c)

# Filter BottomSheet
![Screenshot_20231205-142522](https://github.com/KaulSalil/TT_CryptoMarket/assets/8721680/2869a6bd-4727-4fda-9c69-fc051a39e43b)


