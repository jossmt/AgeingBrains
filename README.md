# AgeingBrains
## Pre-requisites
1. Install Java and make sure it is on your classpath (java8 upwards will do).
2. Install Node.js - this is required to pull libraries from npm. See: https://nodejs.dev/learn/how-to-install-nodejs
3. Install an IDE to work with the code and execute it. I recommend Intellij (community version is fine, or you can install
Intellij Ultimate for free by getting the free student access)
4. Git clone the repo to a local file location

## Running the code
### Frontend
cd to the BrainUi/src directory. Delete node_modules directory if it exists. Delete package-lock.json if it exists.
  - npm install --force
  - ng serve

## Running the backend
Once you install Intellij, File -> Open... -> Find the git cloned repo -> Open from the build.gradle file in the root directory.
Option 1: In src/main/java/com/brain/core there is a java file: BrainApplication.java, open this and you should see a green play button which will
run the backend.
Option 2: On the top toolbar of Intellij there is a dropdown for edit configurations. There should be an option BrainProject, select this
and click the green play button on the right of the dropdown to run the backend.
