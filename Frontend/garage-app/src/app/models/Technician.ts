/** Backend `TechnicianResponseDTO`. */
export interface Technician {
  id: number;
  fullName: string;
  email: string;
  phone: string;
  specialization: string | null;
  salary: number | null;
  isAvailable: boolean | null;
  isActive: boolean | null;
}

/** Backend `TechnicianRequestDTO`. */
export interface TechnicianRequest {
  fullName: string;
  email: string;
  phone: string;
  specialization?: string;
  salary: number;
}

/** Backend `TechnicianAssignmentDTO` (body of POST /assignments/{id}/location). */
export interface LocationUpdateRequest {
  assignmentId: number;
  latitude: number;
  longitude: number;
}
