module.exports={
    preset: 'jest-preset-angular',
    setupFiles: ['<rootDir>/jest-polyfill.js'],
    setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
    testPathIgnorePatterns: [
        '<rootDir>/node_modules/',
        '<rootDir>/dist/'
    ],
    moduleNameMapper:{
        '^src/(.*)$': '<rootDir>/src/$1',
    },
    transform:{
        '^.+\\.(ts|js|mjs|html)$':[
            'jest-preset-angular',
            {
                tsconfig: '<rootDir>/tsconfig.spec.json',
                stringifyContentPathRegex: '\\.(html|svg)$',
            },
        ],
    },
};