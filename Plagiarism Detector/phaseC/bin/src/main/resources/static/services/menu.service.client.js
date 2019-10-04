(function(){
	angular
	.module("UserDemo")
	.factory("MenuService", MenuService);



  function MenuService($http) {

    var api = {
      "setMenu": setMenu,
      "getMenu": getMenu
    };
    return api;

		var menu = {}

		function setMenu(menuItems){
      console.log("MENU")
			menu = menuItems;
		}

		function getMenu(){
			return menu;
		}

	}

})();
