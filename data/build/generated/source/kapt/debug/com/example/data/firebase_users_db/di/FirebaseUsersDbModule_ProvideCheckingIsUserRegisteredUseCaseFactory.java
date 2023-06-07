// Generated by Dagger (https://dagger.dev).
package com.example.data.firebase_users_db.di;

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository;
import com.example.domain.firebase_users_db.usecase.CheckingIsUserRegisteredUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class FirebaseUsersDbModule_ProvideCheckingIsUserRegisteredUseCaseFactory implements Factory<CheckingIsUserRegisteredUseCase> {
  private final Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider;

  public FirebaseUsersDbModule_ProvideCheckingIsUserRegisteredUseCaseFactory(
      Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider) {
    this.firebaseUsersDbRepositoryProvider = firebaseUsersDbRepositoryProvider;
  }

  @Override
  public CheckingIsUserRegisteredUseCase get() {
    return provideCheckingIsUserRegisteredUseCase(firebaseUsersDbRepositoryProvider.get());
  }

  public static FirebaseUsersDbModule_ProvideCheckingIsUserRegisteredUseCaseFactory create(
      Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider) {
    return new FirebaseUsersDbModule_ProvideCheckingIsUserRegisteredUseCaseFactory(firebaseUsersDbRepositoryProvider);
  }

  public static CheckingIsUserRegisteredUseCase provideCheckingIsUserRegisteredUseCase(
      FirebaseUsersDbRepository firebaseUsersDbRepository) {
    return Preconditions.checkNotNullFromProvides(FirebaseUsersDbModule.INSTANCE.provideCheckingIsUserRegisteredUseCase(firebaseUsersDbRepository));
  }
}