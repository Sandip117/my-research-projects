
from unittest import TestCase
from unittest import mock
from mgz_to_jpg.mgz_to_jpg import mgz_to_jpg


class mgz_to_jpgTests(TestCase):
    """
    Test mgz_to_jpg.
    """
    def setUp(self):
        self.app = mgz_to_jpg()

    def test_run(self):
        """
        Test the run code.
        """
        args = []
        if self.app.TYPE == 'ds':
            args.append('inputdir') # you may want to change this inputdir mock
        args.append('outputdir')  # you may want to change this outputdir mock

        # you may want to add more of your custom defined optional arguments to test
        # your app with
        # eg.
        # args.append('--custom-int')
        # args.append(10)

        options = self.app.parse_args(args)
        self.app.run(options)

        # write your own assertions
        self.assertEqual(options.outputdir, 'outputdir')
