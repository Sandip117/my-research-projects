(function () {
	angular
	.module("UserDemo")
	.config(configuration);

	function configuration($routeProvider,$httpProvider) {

		$httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
		$httpProvider.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';

		$routeProvider
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
		.when("/student/",{
			templateUrl: 'views/user/template/student.view.client.html',
			controller: 'studentController',
			controllerAs: 'model'
		})
		.when("/instructor/",{
			templateUrl: 'views/user/template/instructor.home.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/admin/",{
			templateUrl: 'views/user/template/admin.view.client.html',
			controller: 'adminController',
			controllerAs: 'model'
		})
		.when("/admin/updateUser/:uemail",{
			templateUrl: 'views/user/template/admin.updateuser.view.client.html',
			controller: 'adminUpdateController',
			controllerAs: 'model'
		})
		.when("/admin/addUser",{
			templateUrl: 'views/user/template/adduser.view.client.html',
			controller: 'adminAddUserController',
			controllerAs: 'model'
		})
		.when("/admin/setSemester",{
			templateUrl: 'views/user/template/setsemester.view.client.html',
			controller: 'adminAddUserController',
			controllerAs: 'model'
		})
		.when("/instructor/addCourse",{
			templateUrl: 'views/user/template/addcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/newAssignment/:cid",{
			templateUrl: 'views/user/template/new-assignment.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/editCourse/:cid" ,{
			templateUrl: 'views/user/template/editcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/newCourse",{
			templateUrl: 'views/user/template/addcourse.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/assignment/:aid", {
			templateUrl: 'views/user/template/instructor.submissionview.view.client.html',
			controller: 'instructorSubmissionController',
			controllerAs: 'model'
		})
		.when("/instructor/course/:cid", {
			templateUrl: 'views/user/template/instructor.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/instructor/editassignment/:aid", {
			templateUrl: 'views/user/template/editassignment.view.client.html',
			controller: 'instructorController',
			controllerAs: 'model'
		})
		.when("/student/course/:cid", {
			templateUrl: 'views/user/template/student.view.client.html',
			controller: 'studentController',
			controllerAs: 'model'
		})
		.when("/student/course/:cid/assignment/:aid", {
			templateUrl: 'views/user/template/student.assignmentupload.view.client.html',
			controller: 'assignmentController',
			controllerAs: 'model'
		})
		.when("/student/course_reg/",{
			templateUrl: 'views/user/template/studentcourseregistration.view.client.html',
			controller: 'courseRegController',
			controllerAs: 'model'
		})
		.otherwise({
			redirectTo: '/'
		});
	}
})();
