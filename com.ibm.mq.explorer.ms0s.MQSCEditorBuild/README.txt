This package contains just the material needed to build the plugins and feature for the MS0S MQSC Editor.

It no longer builds as an Eclipse Update site. Instead, a simple zip file is created that can be 
unpacked into the eclipse/dropins directory underneath your MQ Explorer installation.

The repo does not include source for the documentation; only a PDF was available when it
was created. So there will be 
an addendum document (README.md) that covers any relevant notable information.

The build.xml file in this directory can be run via ant to create a zip file 
containing the plugins.

Eclipse sometimes does not recognise newly-updated plugins in the dropins directory. One
way to deal with that is to delete the package under dropins, start the MQ Explorer, 
stop it, unpack this code again, and then restart Explorer. That usually does the trick.
Sometimes using the "-initialize" flag to Eclipse is also needed.