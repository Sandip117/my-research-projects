(function(){
    angular
        .module("UserDemo")
        .factory("InstructorService",InstructorService);
    
   
    function InstructorService($http) {
    	var api = {
            "getAllAssignments":getAllAssignments,
            "deleteAssignment": deleteAssignment,
            "deleteCourse": deleteCourse,
            "addCourse": addCourse,
            "addAssignment": addAssignment,
            "getAllCourses":getAllCourses,
            "getAllSemesters": getAllSemesters,
            "getSemesterById": getSemesterById,
            "getCourseById": getCourseById,
            "getAssignmentById": getAssignmentById,
            "updateAssignment": updateAssignment,
            "getCourseBySemester": getCourseBySemester
        };
        return api;   
        function getAllAssignments(courseId){
        	return $http.get("/api/assignment?courseId="+courseId);
        }
        function getAllCourses(userId){
        	return $http.get("/api/courses?userId="+userId);
        }
        function addAssignment(assignment){
        	return $http.post("/api/assignment",assignment);
        }
        function addCourse(course){
        	return $http.post("/api/course",course);
        }
        function deleteCourse(courseId,userId){
        	return $http.delete("/api/course?courseId="+courseId+"&userId="+userId);
        }
        function deleteAssignment(assignmentId,courseId){
        	return $http.delete("/api/assignment?assignmentId="+assignmentId+"&courseId="+courseId);
        }
        function getAllSemesters(){
        	return $http.get("/api/semesters");
        }
        
        function getSemesterById(sid){
        	console.log(sid);
        	return $http.get("/api/semester?semId="+sid);
        }
        
        function getCourseById(cid){
        	return $http.get("/api/course?courseId="+cid);
        }
        function getAssignmentById(aid){
        	return $http.get("/api/assignments?assignmentId="+aid);
        }
        function updateAssignment(assignment){
        		return $http.put("/api/assignment", assignment)
        }
        
        function getCourseBySemester(semester){
        	return $http.post("/api/semester/course", semester);
        }
        
        
    }
})();