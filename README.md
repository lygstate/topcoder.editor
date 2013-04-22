topcoder.editor
===============

The editor for topcoder to automatically generate C/C++, Java, C# source code and test cases.
So can easily test the code with the examples given by topcoder.
Those code first comes from FileEdit, CodeProcessor and ExampleBuilder; if the author doesn't glad me to directly copy these code or have license problem, please let me known.

ExampleProcessor should have the setting page for
TODO: userDefinedTags
TODO: The output indent is tab|space. (2 space, 4 space).

done: Try to automatically genearte package data for Java.

done: 1. Select if write HTML file. The extension is always .html
done: 2. Select if generate the problem description into code.
done: 3. Select if generate the problem description in text file. Extension specified by user.
These three option can co-exist. So user didn't bother the relationship between these options.

done: 4. Automatically Compare and Save preferences. building the relationship 
between the local variable with the preferences saved in the Preferences class.

done: 5. Generate separate directory for different Problem Set(SRMs).
