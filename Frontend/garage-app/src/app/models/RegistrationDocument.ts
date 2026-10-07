import type { RequestStatus } from "./enums";

/** Backend `OwnerRegistrationRequest` (multipart/form-data). */
export interface OwnerRegistrationRequest {
  fullName: string;
  garageName: string;
  email: string;
  phone: string;
  password: string;
  commercialRegisterNumber: string;
  certificateFile: File;
  governorate: string;
  state: string;
  latitude: number;
  longitude: number;
  mapAddress?: string;
}

/** Backend `RegistrationDocumentResponse`. */
export interface RegistrationDocument {
  id: number;
  ownerName: string;
  ownerEmail: string;
  ownerPhone: string;
  garageName: string;
  commercialRegisterNumber: string;
  registerCertificateFile: string;
  governorate: string;
  state: string;
  googleMapsUrl: string | null;
  status: RequestStatus;
}

/** Backend `DecisionRequestDTO`. */
export interface DecisionRequest {
  status: RequestStatus;
  reason?: string;
}
