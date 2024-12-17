export enum needType{
    Volunteer = '0',
    Supplies = '1',
    Monetary = '2'
}

export interface Need{
    id: number;
    name: string;
    cost: number;
    quantity: number;
    type: needType;
}