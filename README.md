# cs-openstack
This repository contains the integration with Openstack API

# References: 
https://developer.openstack.org/api-ref/compute/
https://developer.openstack.org/api-ref/compute/#servers-run-an-action-servers-action
https://developer.openstack.org/api-ref/identity/v3/#authentication-and-token-management

* Available operations (so far... ) are: 
    * compute > actions > api: **GetApiVersionDetails**, **ListAllMajorVersions**
    * compute > actions > servers: **DeleteServer**, **ListServers** 
    * identity > actions > **PasswordAuthenticationWithUnscopedAuthorization**

# Code Conventions
* Code should be: clean, readable, and documented (or references present)
* Add unit tests for any meaningful feature or code change, to prove it
* Add Java docs when it makes sens/it helps
* Maintain as much as possible the .gitignore file / use a global .gitignore
* Formatting: Use IntelliJ IDEA default as much as possible
* Don’t add IDE specific files or configurations 

# Last but not least
https://opensource.com/open-source-way
