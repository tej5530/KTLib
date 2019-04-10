# KTLib
The KTLib is used for faster developemt of any native android project.
# Integration
<b>project level build.gradle</b> </br>
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}</br>
  <b>app level build.gradle</b></br>
  dependencies {
	        implementation 'com.github.tej5530:KTLib:1.6'
	}
