import { Need } from "./need";

export interface WishListResponse {
    userId: number;
    wishList: Need[];
  }