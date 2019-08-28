# Archimedes

A tool for graphical database design and management.


## Build

To build Archimedes application you have to do a few steps before:

### Requirements

The Archimedes application should be build with:

Maven: 3.5.x

Java: 1.8


### Add libraries

Archimedes requires a few libraries which are not available in the internet by this time.

Execute the script `install-libs.sh` to load those libraries from the `lib` folder to your local maven repository.

Thereafter it should be possible to build the Archimedes application with `mvn clean install`.


## Run

To run the Archimedes application start the script `run.sh`.
