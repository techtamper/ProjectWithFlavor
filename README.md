# ProjectWithFlavors

Here is everything you need to know about product flavors and real-time problems with solutions.

Product flavors are nothing but preparing different dishes using the same core ingredients but adding minor flavor toppings to get a different taste. To better understand product flavors let us consider a example:

You ever seen Free and Pro versions of the same app in playstore, Pro version have all features of the free version, also with pro features.
# Build Variants

Build Variants are formed by Build Types and Product Flavors.

According to the Google documentation, build variants are the result of Gradle using a specific set of rules to combine settings, code, and resources configured in your build types and product flavors.


**Build Type** applies different build and packaging settings. An example of build types are “Debug” and “Release”.

**Product Flavors** specify different features and device requirements, such as custom source code, resources, and minimum API levels.


## Product Flavors

           productFlavors {
            def STRING = "String"
            def URL = "BASE_URL"
            food {
                dimension "product"
                applicationIdSuffix ".food"
                versionCode 1
                versionName "0.0.1"
    //            archivesBaseName = "${applicationName}-v${versionCode}-${versionName}"
            }
            ecom {
                dimension "product"
                applicationIdSuffix ".ecom"
                versionCode 1
                versionName "0.0.1"
                archivesBaseName = "${applicationName}-v${versionCode}-${versionName}"
            }
            qa {
                dimension "server"
                applicationIdSuffix ".qa"
                //versionNameSuffix "-qa"
                buildConfigField "boolean", "IS_PROD", "false"
                buildConfigField STRING, URL, "${QA_URL}"
                buildConfigField "String", "BASE_URL", '"https://www.flickr.com/services/"'
            }
            uat {
                dimension "server"
                applicationIdSuffix ".uat"
                //versionNameSuffix "-uat"
                buildConfigField "boolean", "IS_PROD", "false"
                buildConfigField STRING, URL, "${UAT_URL}"
                buildConfigField "String", "BASE_URL", '"https://www.flickr.com/services/"'
            }
            prod {
                dimension "server"
                applicationIdSuffix ".prod"
                buildConfigField "boolean", "IS_PROD", "false"
                buildConfigField STRING, URL, "${UAT_URL}"
                buildConfigField "String", "BASE_URL", '"https://www.flickr.com/services/"'
            }
        }
        
        
## In gradle.properties file
        
            # QA URL
            QA_URL="https://www.flickr.com/services/"

            # UAT URL
            UAT_URL="https://www.flickr.com/services/"
            
            
            
<img src="https://github.com/webaddicted/SocialLogin/blob/master/screenshot/login.png" width="400"> 
            

Build the project and once done, select **Build > Select Build Variant** in the menu bar, and you will see the different Build Variants auto-generated when you added the Product flavors.
