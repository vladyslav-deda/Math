// Generated by Dagger (https://dagger.dev).
package com.example.data.firebase_users_db.di;

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository;
import com.example.domain.firebase_users_db.usecase.RegisterNewUserUseCase;
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
public final class FirebaseUsersDbModule_ProvideRegisterNewUserUseCaseFactory implements Factory<RegisterNewUserUseCase> {
  private final Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider;

  public FirebaseUsersDbModule_ProvideRegisterNewUserUseCaseFactory(
      Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider) {
    this.firebaseUsersDbRepositoryProvider = firebaseUsersDbRepositoryProvider;
  }

  @Override
  public RegisterNewUserUseCase get() {
    return provideRegisterNewUserUseCase(firebaseUsersDbRepositoryProvider.get());
  }

  public static FirebaseUsersDbModule_ProvideRegisterNewUserUseCaseFactory create(
      Provider<FirebaseUsersDbRepository> firebaseUsersDbRepositoryProvider) {
    return new FirebaseUsersDbModule_ProvideRegisterNewUserUseCaseFactory(firebaseUsersDbRepositoryProvider);
  }

  public static RegisterNewUserUseCase provideRegisterNewUserUseCase(
      FirebaseUsersDbRepository firebaseUsersDbRepository) {
    return Preconditions.checkNotNullFromProvides(FirebaseUsersDbModule.INSTANCE.provideRegisterNewUserUseCase(firebaseUsersDbRepository));
  }
}
