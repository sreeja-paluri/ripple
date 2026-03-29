class AppConstants {
  AppConstants._();

  //API
  //static const String baseUrl = 'http://10.0.2.2:8080'; // Android emulator → localhost
  static const String userServiceUrl = 'http://localhost:8080'; // iOS simulator
  static const String friendServiceUrl = 'http://localhost:8081';

  static const Duration requestTimeout = Duration(seconds: 15);

  //Storage Keys
  static const String tokenKey = 'auth_token';

  //Routes
  static const String routeLogin = '/login';
  static const String routeRegister = '/register';
  static const String routeHome = '/home';

  //UserId
  static const String userIdKey = 'user_id';
}
