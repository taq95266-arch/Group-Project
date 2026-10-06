
export interface RegisterDocumnet {
  fullName: string;
  garageName: string;
  email: string;
  phone: string;
  password?: string; 
  commercialRegisterNumber: string;
  certificateFile: File;
  governorate: string;
  state: string;
  latitude: number;
  longitude: number;
  mapAddress?: string; 
}



