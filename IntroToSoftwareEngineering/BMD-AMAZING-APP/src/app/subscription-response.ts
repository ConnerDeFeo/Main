import { Need } from "./need";

export interface NeedDatePair {
    need: Need;
    date: string;
}

export interface SubscriptionResponse {
    userId: number;
    unprocessedSubscriptions: Need[];
    subscriptions: NeedDatePair[];
}