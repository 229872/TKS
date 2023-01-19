interface User {
  login: string,
}

export interface Client extends User {
  firstName: string,
  lastName: string
  address: {
    city: string,
    street: string,
    streetNr: string
  }
}

export interface Employee extends User {
  desk: string
}

export interface Admin extends User {
  favouriteIceCream: string
}

