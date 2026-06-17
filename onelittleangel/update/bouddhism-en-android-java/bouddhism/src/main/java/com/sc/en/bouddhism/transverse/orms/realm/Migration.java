package com.sc.en.bouddhism.transverse.orms.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class Migration implements RealmMigration {
  /**
   * This method will be called if a migration is needed. The entire method is wrapped in a
   * write transaction so it is possible to create/change or delete any existing objects
   * without wrapping it in your own transaction.
   *
   * @param realm      the Realm schema on which to perform the migration.
   * @param oldVersion the schema version of the Realm at the start of the migration.
   * @param newVersion the schema version of the Realm after executing the migration.
   */
  @Override
  public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    if (oldVersion == 0) {
      // Migrate from v0 to v1
      oldVersion++;
    }

    if (oldVersion == 1) {
      // Migrate from v1 to v2
      oldVersion++;
    }

    if (oldVersion == 2) {
      // Migrate from v2 to v3
      oldVersion++;
    }

    if (oldVersion < newVersion) {
      //throw new IllegalStateException(String.format(" missing from v%d to v%d", oldVersion, newVersion));
    }
  }
}
