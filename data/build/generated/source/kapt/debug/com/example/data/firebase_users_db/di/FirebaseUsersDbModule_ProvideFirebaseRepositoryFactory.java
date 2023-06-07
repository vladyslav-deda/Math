// Generated by Dagger (https://dagger.dev).
package com.example.data.firebase_users_db.di;

import com.example.domain.firebase_users_db.FirebaseUsersDbRepository;
import com.google.firebase.database.DatabaseReference;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("javax.inject.Named")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class FirebaseUsersDbModule_ProvideFirebaseRepositoryFactory implements Factory<FirebaseUsersDbRepository> {
  private final Provider<DatabaseReference> usersDbReferenceProvider;

  public FirebaseUsersDbModule_ProvideFirebaseRepositoryFactory(
      Provider<DatabaseReference> usersDbReferenceProvider) {
    this.usersDbReferenceProvider = usersDbReferenceProvider;
  }

  @Override
  public FirebaseUsersDbRepository get() {
    return provideFirebaseRepository(usersDbReferenceProvider.get());
  }

  public static FirebaseUsersDbModule_ProvideFirebaseRepositoryFactory create(
      Provider<DatabaseReference> usersDbReferenceProvider) {
    return new FirebaseUsersDbModule_ProvideFirebaseRepositoryFactory(usersDbReferenceProvider);
  }

  public static FirebaseUsersDbRepository provideFirebaseRepository(
      DatabaseReference usersDbReference) {
    return Preconditions.checkNotNullFromProvides(FirebaseUsersDbModule.INSTANCE.provideFirebaseRepository(usersDbReference));
  }
}
