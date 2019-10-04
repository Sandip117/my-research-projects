(function(){
	angular
	.module("UserDemo")
	.factory("SemesterService",SemesterService);


	function SemesterService($http) {
		var api = {
				"addStudentCourse":addStudentCourse,
				"getAllCourses":getAllCourses,
				"deleteCourse": deleteCourse
		};
		return api;

		function addStudentCourse(studentcourse){
			return $http.post("/api/studentcourse",studentcourse);
		}
		function getAllCourses(userId){
			return $http.get("/api/studentcourse?userId="+userId);
		}

		function deleteCourse(rid){
			return $http.delete("/api/studentcourse?regId="+rid);
		}
	}

})();
