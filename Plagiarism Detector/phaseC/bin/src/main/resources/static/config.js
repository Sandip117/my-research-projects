(function () {
	angular
	.module("UserDemo")
	.config(configuration);

	function configuration($routeProvider,$httpProvider) {

		$httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';

		$routeProvider
		.when("/login",{
			templateUrl: 'views/user/template/login.view.client.html',
			controller: 'loginController',
			controllerAs: 'model'
		})
		.when("/",{
			templateUrl: 'views/user/template/login.view.client.html',
			controller: 'loginController',
			controllerAs: 'model'
		})
		.when("#/",{
			templateUrl: 'views/user/template/login.view.client.html',
			controller: 'loginController',
			controllerAs: 'model'
		})
		.when("/user/:uid",{
                templateUrl: 'views/user/template/profile.view.client.html',
                controller: 'profileController',
                controllerAs: 'model'
		})
		.when("/register",{
			templateUrl: 'views/user/template/login.view.client.html',
			controller: 'registerController',
			controllerAs: 'model'
		})
		.when("/student/:uid",{
			templateUrl: 'views/user/template/student.view.client.html',
			controller: 'studentController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid",{
			templateUrl: 'views/user/template/instructor.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/admin/:uid",{
			templateUrl: 'views/user/template/admin.view.client.html',
			controller: 'adminController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/addCourse",{
			templateUrl: 'views/user/template/addcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/newAssignment/:cid",{
			templateUrl: 'views/user/template/new-assignment.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/editCourse/:cid" ,{
			templateUrl: 'views/user/template/editcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/newCourse",{
			templateUrl: 'views/user/template/addcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/assignment/:aid", {
			templateUrl: 'views/user/template/instructor.submissionview.view.client.html',
			controller: 'instructorSubmissionController',
			controllerAs: 'model'
		})
		.when("/instructor/:uid/editassignment/:aid", {
			templateUrl: 'views/user/template/editassignment.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/student/:uid/course/:cid", {
			templateUrl: 'views/user/template/student.view.client.html',
			controller: 'studentController',
			controllerAs: 'model'
		})
		.when("/student/:uid/course/:cid/assignment/:aid", {
			templateUrl: 'views/user/template/student.assignmentupload.view.client.html',
			controller: 'assignmentController',
			controllerAs: 'model'
		})
		.when("/student/:uid/course_reg/",{
			templateUrl: 'views/user/template/studentcourseregistration.view.client.html',
			controller: 'courseRegController',
			controllerAs: 'model'
		});
	}
})();
