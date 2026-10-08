export interface ServiceCustomerRequest {
  requestId: number;
  garageId: number;
  garageName: string;
  garageOptionId: number;
  guestName: string;
  guestPhone: string;
  carMakeModel: string;
  carPlateNumber: string;
  appliedPrice: number;
  status: "PENDING" | "ACCEPTED" | "IN_PROGRESS" | "COMPLETED" | "CANCELLED";
  createdAt: string;
  latitude: number | null;
  longitude: number | null;
}
export interface Technician {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  specialization: string | null;
  salary: number | null;
  isAvailable: boolean;
  active: boolean;
}
