/** Backend `ServiceRequest` / `ServiceResponse`. */
export interface ServiceRequest {
  name: string;
}
export interface ServiceItem {
  serviceId: number;
  name: string;
}

/** Backend `ServiceOptionRequest` / `ServiceOptionResponse`. */
export interface ServiceOptionRequest {
  type: string;
  size?: string;
  brand?: string;
}
export interface ServiceOption {
  serviceOptionId: number;
  serviceId: number;
  type: string;
  size: string | null;
  brand: string | null;
}
