//
//  EventListViewModel.swift
//  iosApp
//
//  Created by GERARD on 16/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SharedDi
import KMPNativeCoroutinesAsync

enum EventListUiState {
    case loading
    case success(EventItemListUi)
    case failure
}

@MainActor
class EventListViewModel: ObservableObject {
    private let repository: EventRepository = RepositoryHelper().eventRepository

    @Published var uiState: EventListUiState = EventListUiState.loading

    private var eventListTask: Task<(), Never>?

    func fetchEventList() {
        eventListTask = Task {
            if let error = await asyncError(for: repository.fetchAndStoreEventList()) {
                // ignored
            }
            do {
                let stream = asyncSequence(for: repository.events())
                for try await events in stream {
                    self.uiState = .success(events)
                }
            } catch {
                self.uiState = .failure
            }
        }
    }

    func stop() {
        eventListTask?.cancel()
    }
}
