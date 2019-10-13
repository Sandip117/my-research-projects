pl-mgz_to_numpy
================================

.. image:: https://badge.fury.io/py/mgz_to_numpy.svg
    :target: https://badge.fury.io/py/mgz_to_numpy

.. image:: https://travis-ci.org/FNNDSC/mgz_to_numpy.svg?branch=master
    :target: https://travis-ci.org/FNNDSC/mgz_to_numpy

.. image:: https://img.shields.io/badge/python-3.5%2B-blue.svg
    :target: https://badge.fury.io/py/pl-mgz_to_numpy

.. contents:: Table of Contents


Abstract
--------

An app to ...


Synopsis
--------

.. code::

    python mgz_to_numpy.py                                           \
        [-v <level>] [--verbosity <level>]                          \
        [--version]                                                 \
        [--man]                                                     \
        [--meta]                                                    \
        <inputDir>
        <outputDir> 

Description
-----------

``mgz_to_numpy.py`` is a ChRIS-based application that...

Agruments
---------

.. code::

    [-v <level>] [--verbosity <level>]
    Verbosity level for app. Not used currently.

    [--version]
    If specified, print version number. 
    
    [--man]
    If specified, print (this) man page.

    [--meta]
    If specified, print plugin meta data.


Run
----

This ``plugin`` can be run in two modes: natively as a python package or as a containerized docker image.

Using PyPI
~~~~~~~~~~

To run from PyPI, simply do a 

.. code:: bash

    pip install mgz_to_numpy

and run with

.. code:: bash

    mgz_to_numpy.py --man /tmp /tmp

to get inline help. The app should also understand being called with only two positional arguments

.. code:: bash

    mgz_to_numpy.py /some/input/directory /destination/directory


Using ``docker run``
~~~~~~~~~~~~~~~~~~~~

To run using ``docker``, be sure to assign an "input" directory to ``/incoming`` and an output directory to ``/outgoing``. *Make sure that the* ``$(pwd)/out`` *directory is world writable!*

Now, prefix all calls with 

.. code:: bash

    docker run --rm -v $(pwd)/out:/outgoing                             \
            fnndsc/pl-mgz_to_numpy mgz_to_numpy.py                        \

Thus, getting inline help is:

.. code:: bash

    mkdir in out && chmod 777 out
    docker run --rm -v $(pwd)/in:/incoming -v $(pwd)/out:/outgoing      \
            fnndsc/pl-mgz_to_numpy mgz_to_numpy.py                        \
            --man                                                       \
            /incoming /outgoing

Examples
--------





