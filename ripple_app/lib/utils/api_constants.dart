class AppConstants {
  AppConstants._();

  //API
  //static const String baseUrl = 'http://10.0.2.2:8080'; // Android emulator → localhost
  static const String baseUrl = 'http://localhost:8080'; // iOS simulator

  static const Duration requestTimeout = Duration(seconds: 15);

  //Storage Keys
  static const String tokenKey = 'auth_token';

  //Routes
  static const String routeLogin = '/login';
  static const String routeRegister = '/register';
  static const String routeHome = '/home';
}
