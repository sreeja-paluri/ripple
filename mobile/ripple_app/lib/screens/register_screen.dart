import 'package:flutter/material.dart';
import 'package:ripple_app/service/auth_service.dart';
import 'package:ripple_app/widgets/api_widgets.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _usernameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _authService = AuthService();

  bool _isLoading = false;
  String? _usernameError;
  String? _emailError;
  String? _passwordError;

  @override
  void dispose() {
    _usernameController.dispose();
    _emailController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  bool _validate() {
    bool valid = true;
    setState(() {
      _usernameError = null;
      _emailError = null;
      _passwordError = null;

      final username = _usernameController.text.trim();
      final email = _emailController.text.trim();
      final password = _passwordController.text;

      if (username.isEmpty || username.length < 3) {
        _usernameError = 'Username must be at least 3 characters';
        valid = false;
      }
      if (email.isEmpty || !RegExp(r'^[^@]+@[^@]+\.[^@]+').hasMatch(email)) {
        _emailError = 'Enter a valid email address';
        valid = false;
      }
      if (password.isEmpty || password.length < 6) {
        _passwordError = 'Password must be at least 6 characters';
        valid = false;
      }
    });
    return valid;
  }

  Future<void> _register() async {
    if (!_validate()) return;

    setState(() => _isLoading = true);

    try {
      final response = await _authService.register(
        username: _usernameController.text.trim(),
        email: _emailController.text.trim(),
        password: _passwordController.text,
      );

      if (!mounted) return;

      if (response.isSuccess) {
        showSuccessSnackbar(context, 'Account created! Please log in.');
        Navigator.of(context).pop(); // Back to LoginScreen
      } else {
        showErrorSnackbar(context, response.message);
      }
    } catch (e) {
      if (mounted) {
        showErrorSnackbar(context, 'Registration failed. Please try again.');
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Create Account')),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(24),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Join Ripple',
                style: Theme.of(context).textTheme.headlineMedium?.copyWith(
                      fontWeight: FontWeight.bold,
                    ),
              ),
              const SizedBox(height: 8),
              const Text('Create your account to get started'),
              const SizedBox(height: 40),
              AppTextField(
                label: 'Username',
                controller: _usernameController,
                errorText: _usernameError,
              ),
              const SizedBox(height: 16),
              AppTextField(
                label: 'Email',
                controller: _emailController,
                errorText: _emailError,
                keyboardType: TextInputType.emailAddress,
              ),
              const SizedBox(height: 16),
              AppTextField(
                label: 'Password',
                controller: _passwordController,
                errorText: _passwordError,
                obscureText: true,
                textInputAction: TextInputAction.done,
              ),
              const SizedBox(height: 24),
              LoadingButton(
                label: 'Create Account',
                onPressed: _register,
                isLoading: _isLoading,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
