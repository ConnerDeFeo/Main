import { Need } from "./need";

export interface BasketResponse {
    userId: number;
    totalCost: number;
    basket: Need[];
  }
  