package in.transportstack.delhi.core.entity.type;

import lombok.Getter;

@Getter
public enum UploadType {
     DATASET_DOCUMENTATION("Documentation"),
     DATASET_DATASET_FILE("DataSet File"),
     DATASET_LICENSE("License"),
     SERVICE_DOCUMENTATION("Documentation"),
     SERVICE_LICENSE("License");

     private final String value;

     private UploadType(String value) {
          this.value = value;
     }
}
